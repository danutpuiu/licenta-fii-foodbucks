using Data.Domain.Entities.RecipeEntities;
using Data.Domain.Entities.OperationEntities;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace Data.Domain.Interfaces
{
    public interface IRecipesRepository : IGenericRepository<Recipe>
    {
        Task UpdateCost();
        Task<IEnumerable<Recipe>> GetByName(string name, Task<IEnumerable<Recipe>> recipes);
        Task<IEnumerable<Recipe>> GetByDescription(string description, Task<IEnumerable<Recipe>> recipes);
        Task<IEnumerable<Recipe>> GetByServings(int servings, Task<IEnumerable<Recipe>> recipes);
        Task<IEnumerable<Recipe>> GetByCalories(int calories, Task<IEnumerable<Recipe>> recipes);
        Task<IEnumerable<Recipe>> GetByCookingTime(int cookingTime, Task<IEnumerable<Recipe>> recipes);
        Task<IEnumerable<Recipe>> GetByLikes(int likes, Task<IEnumerable<Recipe>> recipes);
        Task<IEnumerable<Recipe>> GetByVotes(int votes, Task<IEnumerable<Recipe>> recipes);
        Task<IEnumerable<Recipe>> GetByRating(double RecipeRatingType, Task<IEnumerable<Recipe>> recipes);

        Task<IEnumerable<Recipe>> GetByFilter(RecipeFilter filter);

        Task<IEnumerable<Recipe>> SortBy(string sortCriteria, string sortType);
    }
}
