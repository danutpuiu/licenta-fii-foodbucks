using Data.Domain.Entities.RecipeEntities;
using Data.Domain.Interfaces;
using Microsoft.AspNetCore.Mvc;
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
        
        public RecipesController(IRecipesRepository recipesRepository,
            IInstructionsRepository instructionsRepository,
            IIngredientsRepository ingredientsRepository,
            IProductsRepository productsRepository,
            IProductStoresRepository productStoresRepository)
        {
            _recipesRepository = recipesRepository;
            _instructionsRepository = instructionsRepository;
            _ingredientsRepository = ingredientsRepository;
            _productsRepository = productsRepository;
            _productStoresRepository = productStoresRepository;
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
    }
}