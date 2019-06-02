using Data.Domain.Entities.RecipeEntities;
using Data.Domain.Entities.OperationEntities;
using System.Collections.Generic;
using System.Threading.Tasks;
using System;

namespace Data.Domain.Interfaces
{
    public interface IRecipesRepository : IGenericRepository<Recipe>
    {
        Task<IEnumerable<Recipe>> GetByName(string name, Task<IEnumerable<Recipe>> recipes);
        Task<IEnumerable<Recipe>> GetByDescription(string description, Task<IEnumerable<Recipe>> recipes);
        Task<IEnumerable<Recipe>> GetByServings(int servings, Task<IEnumerable<Recipe>> recipes);
        Task<IEnumerable<Recipe>> GetByCalories(int calories, Task<IEnumerable<Recipe>> recipes);
        Task<IEnumerable<Recipe>> GetByCookingTime(int cookingTime, Task<IEnumerable<Recipe>> recipes);
        Task<IEnumerable<Recipe>> GetByLikes(int likes, Task<IEnumerable<Recipe>> recipes);
        Task<IEnumerable<Recipe>> GetByVotes(int votes, Task<IEnumerable<Recipe>> recipes);
        Task<IEnumerable<Recipe>> GetByRating(RecipeRatingType rating, Task<IEnumerable<Recipe>> recipes);
        Task<IEnumerable<Recipe>> GetByCost(double cost, Task<IEnumerable<Recipe>> recipes);
        Task<IEnumerable<Recipe>> GetByBrands(List<Product> products, Task<IEnumerable<Recipe>> recipes);
        Task<IEnumerable<Recipe>> GetByIngredients(List<Product> products, Task<IEnumerable<Recipe>> recipes);
        Task<IEnumerable<Recipe>> GetByStores(List<Store> stores, Task<IEnumerable<Recipe>> recipes);
        Task<double> GetCostById(Guid id);

        Task AddVote(Guid id, bool vote);

        Task UpdateRecipeCost(Guid id);
        Task<IEnumerable<Recipe>> GetByFilter(RecipeFilter filter);

        Task<IEnumerable<Recipe>> SortBy(string sortCriteria, string sortType);
    }
}
