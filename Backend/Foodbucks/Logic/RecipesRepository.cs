using Data.Domain.Entities.OperationEntities;
using Data.Domain.Entities.RecipeEntities;
using Data.Domain.Interfaces;
using Data.Persistence;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Logic
{
    public class RecipesRepository : GenericRepository<Recipe>, IRecipesRepository
    {
        private readonly IDatabaseContext _databaseContext;
        private readonly IInstructionsRepository _instructionsRepository;
        private readonly IIngredientsRepository _ingredientsRepository;
        private readonly IProductStoresRepository _productStoresRepository;

        public RecipesRepository(IDatabaseContext databaseContext, IInstructionsRepository instructionsRepository, IIngredientsRepository ingredientsRepository, IProductStoresRepository productStoresRepository, IProductsRepository productsRepository) : base(databaseContext)
        {
            _databaseContext = databaseContext;
            _instructionsRepository = instructionsRepository;
            _ingredientsRepository = ingredientsRepository;
            _productStoresRepository = productStoresRepository;
        }

        public async Task<IEnumerable<Recipe>> GetByBrands(List<Product> products, Task<IEnumerable<Recipe>> recipes)
        {
            IEnumerable<Recipe> recipesWithIngredients = Enumerable.Empty<Recipe>();

            foreach (Recipe recipe in await recipes)
            {
                bool containsIngredient = false;
                IEnumerable<Ingredient> ingredients = await _ingredientsRepository.GetByRecipe(recipe.Id);
                foreach (Ingredient ingredient in ingredients)
                {
                    ProductStore productStore = await _productStoresRepository.GetById(ingredient.ProductStoreId);
                    foreach (Product product in products)
                    {
                        if (product.Id == productStore.ProductId)
                        {
                            containsIngredient = true;
                            continue;
                        }
                    }
                }

                if (containsIngredient)
                {
                    recipesWithIngredients.Append(recipe);
                }
            }


            return recipesWithIngredients;
        }

        public async Task<IEnumerable<Recipe>> GetByCalories(int calories, Task<IEnumerable<Recipe>> recipes)
        {
            return await _databaseContext.Recipes.Where(recipe =>
               recipe.Calories == calories).ToListAsync();
        }

        public async Task<IEnumerable<Recipe>> GetByCookingTime(int cookingTime, Task<IEnumerable<Recipe>> recipes)
        {
            return await _databaseContext.Recipes.Where(recipe =>
               recipe.CookingTime == cookingTime).ToListAsync();
        }

        public async Task<IEnumerable<Recipe>> GetByCost(double cost, Task<IEnumerable<Recipe>> recipes)
        {
            return await _databaseContext.Recipes.Where(recipe =>
                recipe.Cost <= cost).OrderByDescending(recipe => recipe.Cost).ToListAsync();
        }

        public async Task<IEnumerable<Recipe>> GetByDescription(string description, Task<IEnumerable<Recipe>> recipes)
        {
            return await _databaseContext.Recipes.Where(recipe =>
               recipe.Description.ToLower().Equals(description.ToLower())).ToListAsync();
        }

        public async Task<IEnumerable<Recipe>> GetByFilter(RecipeFilter filter)
        {
            Task<IEnumerable<Recipe>> recipes = GetAll();

            if(filter.Cost > 0)
            {
                recipes = GetByCost(filter.Cost, recipes);
            }
            if(filter.Name != "")
            {
                recipes = GetByName(filter.Name, recipes);
            }
            if(filter.CookingTime != 0)
            {
                recipes = GetByCookingTime(filter.CookingTime, recipes);
            }
            if(filter.IncludingBrands.Count != 0)
            {
                recipes = GetByBrands(filter.IncludingBrands, recipes);
            }
            if(filter.IncludingIngreditents.Count != 0)
            {
                recipes = GetByIngredients(filter.IncludingIngreditents, recipes);
            }
            if(filter.IncludingStores.Count != 0)
            {
                recipes = GetByStores(filter.IncludingStores, recipes);
            }
             

            recipes = GetByRating(filter.Rating, recipes);
            recipes = GetByVotes(filter.Votes, recipes);
            

            return await recipes;
        }

        public async Task<IEnumerable<Recipe>> GetByIngredients(List<Product> products, Task<IEnumerable<Recipe>> recipes)
        {
            IEnumerable<Recipe> recipesWithIngredients = Enumerable.Empty<Recipe>();

            foreach(Recipe recipe in await recipes)
            {
                bool containsIngredient = false;
                IEnumerable<Ingredient> ingredients = await _ingredientsRepository.GetByRecipe(recipe.Id);
                foreach (Ingredient ingredient in ingredients)
                {
                    ProductStore productStore = await _productStoresRepository.GetById(ingredient.ProductStoreId);
                    foreach (Product product in products)
                    {
                        if (product.Id == productStore.ProductId)
                        {
                            containsIngredient = true;
                            continue;
                        }
                    }
                }

                if (containsIngredient)
                {
                    recipesWithIngredients.Append(recipe);
                }
            }


            return recipesWithIngredients;
        }

        public async Task<IEnumerable<Recipe>> GetByLikes(int likes, Task<IEnumerable<Recipe>> recipes)
        {
            return (await recipes).Where(recipe =>
                recipe.Likes >= likes).OrderByDescending(recipe => recipe.Likes);
        }

        public async Task<IEnumerable<Recipe>> GetByName(string name, Task<IEnumerable<Recipe>> recipes)
        {
            return (await recipes).Where(recipe =>
                recipe.Name.ToLower().Contains(name.ToLower()));
        }

        public async Task<IEnumerable<Recipe>> GetByRating(RecipeRatingType rating, Task<IEnumerable<Recipe>> recipes)
        {
            return (await recipes).Where(recipe =>
                recipe.Rating >= rating).OrderBy(recipe => recipe.Rating);
        }

        public async Task<IEnumerable<Recipe>> GetByServings(int servings, Task<IEnumerable<Recipe>> recipes)
        {
            return (await recipes).Where(recipe =>
                recipe.Servings >= servings).OrderBy(recipe => recipe.Servings);
        }

        public async Task<IEnumerable<Recipe>> GetByStores(List<Store> stores, Task<IEnumerable<Recipe>> recipes)
        {
            IEnumerable<Recipe> recipesInStore = Enumerable.Empty<Recipe>();

            foreach (Recipe recipe in await recipes)
            {
                bool containsStore = false;
                IEnumerable<Ingredient> ingredients = await _ingredientsRepository.GetByRecipe(recipe.Id);
                foreach (Ingredient ingredient in ingredients)
                {
                    ProductStore productStore = await _productStoresRepository.GetById(ingredient.ProductStoreId);
                    foreach (Store store in stores)
                    {
                        if(store.Id == productStore.StoreId)
                        {
                            containsStore = true;
                            continue;
                        }
                    }
                }

                if(containsStore)
                {
                    recipesInStore.Append(recipe);
                }
            }

            return recipesInStore;
        }

        public async Task<IEnumerable<Recipe>> GetByVotes(int votes, Task<IEnumerable<Recipe>> recipes)
        {
            return (await recipes).Where(recipe =>
                recipe.Votes >= votes).OrderBy(recipe => recipe.Votes); ;
        }

        public async Task<double> GetCostById(Guid id)
        {
            Recipe recipe = await GetById(id);
            return recipe.Cost;
        }

        public async Task<IEnumerable<Recipe>> SortBy(string sortCriteria, string sortType)
        {
            Task<IEnumerable<Recipe>> recipes = GetAll();

            switch (sortCriteria)
            {
                case "name":
                    switch (sortType)
                    {
                        case "asc":
                            return (await recipes).OrderBy(recipe => recipe.Name);
                        case "desc":
                            return (await recipes).OrderByDescending(recipe => recipe.Name);
                    }
                    break;
                case "cost":
                    switch (sortType)
                    {
                        case "asc":
                            return (await recipes).OrderBy(recipe => recipe.Cost);
                        case "desc":
                            return (await recipes).OrderByDescending(recipe => recipe.Cost);
                    }
                    break;
                case "rating":
                    switch (sortType)
                    {
                        case "asc":
                            return (await recipes).OrderBy(recipe => recipe.Rating);
                        case "desc":
                            return (await recipes).OrderByDescending(recipe => recipe.Rating);
                    }
                    break;
                case "likes":
                    switch (sortType)
                    {
                        case "asc":
                            return (await recipes).OrderBy(recipe => recipe.Likes);
                        case "desc":
                            return (await recipes).OrderByDescending(recipe => recipe.Likes);
                    }
                    break;
                case "servings":
                    switch (sortType)
                    {
                        case "asc":
                            return (await recipes).OrderBy(recipe => recipe.Servings);
                        case "desc":
                            return (await recipes).OrderByDescending(recipe => recipe.Servings);
                    }
                    break;
                case "cookingtime":
                    switch (sortType)
                    {
                        case "asc":
                            return (await recipes).OrderBy(recipe => recipe.CookingTime);
                        case "desc":
                            return (await recipes).OrderByDescending(recipe => recipe.CookingTime);
                    }
                    break;
            }

            return await recipes;
        }

        public async Task UpdateRecipeCost(Guid id)
        {
            Recipe recipe = await GetById(id);
            Task<IEnumerable<Ingredient>> ingredientsInRecipe = _ingredientsRepository.GetByRecipe(recipe.Id);
            double tempCost = 0;
            foreach (Ingredient ingredient in await ingredientsInRecipe)
            {
                tempCost += ingredient.Cost;
            }

            recipe.Update(recipe.Name, recipe.Description, recipe.Servings, recipe.Calories,
                recipe.CookingTime, recipe.Likes, recipe.Votes, recipe.Rating, tempCost);

            await Save();
        }
    }
}
