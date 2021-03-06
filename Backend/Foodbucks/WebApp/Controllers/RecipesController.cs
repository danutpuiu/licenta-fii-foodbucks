﻿using Data.Domain.Entities.OperationEntities;
using Data.Domain.Entities.RecipeEntities;
using Data.Domain.Interfaces;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using WebApp.DTO;

namespace WebApp.Controllers
{
    [Route("[controller]")]
    public class RecipesController : Controller
    {
        private readonly IRecipesRepository _recipesRepository;
        private readonly IInstructionsRepository _instructionsRepository;
        private readonly IIngredientsRepository _ingredientsRepository;
        private readonly IProductStoresRepository _productStoresRepository;
        private readonly IProductsRepository _productsRepository;
        private readonly IStoresRepository _storesRepository;
        
        public RecipesController(IRecipesRepository recipesRepository,
            IInstructionsRepository instructionsRepository,
            IIngredientsRepository ingredientsRepository,
            IProductsRepository productsRepository,
            IProductStoresRepository productStoresRepository,
            IStoresRepository storesRepository)
        {
            _recipesRepository = recipesRepository;
            _instructionsRepository = instructionsRepository;
            _ingredientsRepository = ingredientsRepository;
            _productsRepository = productsRepository;
            _productStoresRepository = productStoresRepository;
            _storesRepository = storesRepository;
        }

        [HttpPut("{id}")]
        [Route("addrating")]
        public async Task<IActionResult> AddNewRating (Guid id, [FromBody] NewVoteDTO newVoteDTO)
        {
            await _recipesRepository.AddVote(id, newVoteDTO.Vote);

            return Ok(_recipesRepository.GetById(id).Result);
        }



        [HttpPost]
        public async Task<IActionResult> Create([FromBody] RecipeDTO recipeDto)
        {

            var recipe = Recipe.Create(recipeDto.Name,
                recipeDto.Description,
                recipeDto.Servings,
                recipeDto.Calories,
                recipeDto.CookingTime,
                0,
                0,
                0,
                0);
            await _recipesRepository.Add(recipe);

            foreach(var ingredient in recipeDto.Ingredients)
            {
                await _ingredientsRepository.AddIngredient(recipe.Id,
                    ingredient.Name,
                    ingredient.Quantity,
                    ingredient.UnitOfMeasurement);
            }

            foreach(var instruction in recipeDto.InstructionSteps)
            {
                await _instructionsRepository.AddInstruction(recipe.Id,
                    instruction.Description,
                    instruction.InstructionNr);
            }
            return Ok(recipe);
        }

        [HttpGet]
        [Route("All")]
        public async Task<IActionResult> GetAll()
        {
            return Ok(await _recipesRepository.GetAll());
        }

        [HttpGet]
        [Route("Sort")]
        public async Task<IActionResult> Sort(string sortBy)
        {
            string sortCriteria = sortBy.Split('_').ToList()[0];
            string sortType = sortBy.Split('_').ToList()[1];
            return Ok(await _recipesRepository.SortBy(sortCriteria, sortType));
        }

        [HttpGet]
        public async Task<IActionResult> Get(string name)
        {
            return Ok(await _recipesRepository.GetByName(name, _recipesRepository.GetAll()));
        }

        [HttpPost]
        [Route("Filter")]
        public async Task<IActionResult> FilterRecipes([FromBody] FilterDTO filterDTO)
        {
            List<Product> includingProducts = new List<Product>();
            List<Product> includingBrands = new List<Product>();
            List<Store> onlyStores = new List<Store>();

            List<string> includedProductsList = filterDTO.IncludedProducts.Split(',').ToList();
            List<string> includingBrandsList = filterDTO.IncludingBrands.Split(',').ToList();
            List<string> onlyStoresList = filterDTO.OnlyStores.Split(',').ToList();

            foreach (var product in includedProductsList)
            {
                includingProducts.AddRange(await _productsRepository.GetByName(product));
            }
            foreach (var brand in includingBrandsList)
            {
                includingBrands.AddRange(await _productsRepository.GetByBrand(brand));
            }
            foreach (var store in onlyStoresList)
            {
                onlyStores.AddRange(await _storesRepository.GetByName(store));
            }

            RecipeFilter filter = new RecipeFilter
            {
                Cost = filterDTO.Cost,
                Name = filterDTO.Name,
                Rating = filterDTO.Rating,
                Votes = filterDTO.VotesNumber,
                CookingTime = filterDTO.CookingTime,
                IncludingIngreditents = includingProducts,
                IncludingBrands = includingBrands,
                OnlyStores = onlyStores
            };

            return Ok(await _recipesRepository.GetByFilter(filter));
        }

        [HttpGet("[action]")]
        public async Task<IActionResult> RecipeDetails(Guid id)
        {
            RecipeDTO recipeDto = new RecipeDTO();

            Recipe recipe = await _recipesRepository.GetById(id);
            await _recipesRepository.UpdateRecipeCost(id);

            recipeDto.Name = recipe.Name;
            recipeDto.Description = recipe.Description;
            recipeDto.Cost = recipe.Cost;
            recipeDto.Calories = recipe.Calories;
            recipeDto.CookingTime = recipe.CookingTime;
            recipeDto.Likes = recipe.Likes;
            recipeDto.Rating = recipe.Rating;
            recipeDto.Votes = recipe.Votes;
            recipeDto.Servings = recipe.Servings;

            List<InstructionStepDTO> instructionStepsDTOs = new List<InstructionStepDTO>();
            IEnumerable<Instruction> instructions = await _instructionsRepository.GetByRecipe(id);
            foreach(var instruction in instructions)
            {
                InstructionStepDTO instructionStepDTO = new InstructionStepDTO
                {
                    Description = instruction.Description,
                    InstructionNr = instruction.InstructionNr
                };
                instructionStepsDTOs.Add(instructionStepDTO);
            }

            List<IngredientDTO> ingredientDTOs = new List<IngredientDTO>();
            IEnumerable<Ingredient> ingredients = await _ingredientsRepository.GetByRecipe(id);
            foreach (var ingredient in ingredients)
            {
                IngredientDTO ingredientDTO = new IngredientDTO
                {
                    Name = ingredient.Name,
                    Cost = ingredient.Cost,
                    Quantity = ingredient.Quantity,
                    UnitOfMeasurement = ingredient.UnitOfMeasurement,
                    NrOfProductsNecessary = ingredient.NrOfProductsNecessary
                };

                ingredientDTOs.Add(ingredientDTO);
            }

            recipeDto.Ingredients = ingredientDTOs;
            recipeDto.InstructionSteps = instructionStepsDTOs;
            

            return Ok(recipeDto);
        }

        [HttpDelete("[action]")]
        public async Task<IActionResult> DeleteRecipe(Guid id)
        {
            await _recipesRepository.Delete(id);
            foreach(var ingredient in await _ingredientsRepository.GetByRecipe(id))
            {
                await _ingredientsRepository.Delete(ingredient.Id);
            }

            foreach(var instruction in await _instructionsRepository.GetByRecipe(id))
            {
                await _instructionsRepository.Delete(instruction.Id);
            }

            return Ok();
        }
        

    }
}