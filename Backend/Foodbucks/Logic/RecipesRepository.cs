using Data.Domain.Entities.OperationEntities;
using Data.Domain.Entities.RecipeEntities;
using Data.Domain.Interfaces;
using Data.Persistence;
using System;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace Logic
{
    public class RecipesRepository : GenericRepository<Recipe>, IRecipesRepository
    {
        private readonly IDatabaseContext _databaseContext;
        private readonly IInstructionsRepository _instructionsRepository;
        private readonly IIngredientsRepository _ingredientsRepository;

        public RecipesRepository(IDatabaseContext databaseContext, IInstructionsRepository instructionsRepository, IIngredientsRepository ingredientsRepository) : base(databaseContext)
        {
            _databaseContext = databaseContext;
            _instructionsRepository = instructionsRepository;
            _ingredientsRepository = ingredientsRepository;
        }

        public Task<IEnumerable<Recipe>> GetByCalories(int calories, Task<IEnumerable<Recipe>> recipes)
        {
            throw new NotImplementedException();
        }

        public Task<IEnumerable<Recipe>> GetByCookingTime(int cookingTime, Task<IEnumerable<Recipe>> recipes)
        {
            throw new NotImplementedException();
        }

        public Task<IEnumerable<Recipe>> GetByDescription(string description, Task<IEnumerable<Recipe>> recipes)
        {
            throw new NotImplementedException();
        }

        public Task<IEnumerable<Recipe>> GetByFilter(RecipeFilter filter)
        {
            throw new NotImplementedException();
        }

        public Task<IEnumerable<Recipe>> GetByLikes(int likes, Task<IEnumerable<Recipe>> recipes)
        {
            throw new NotImplementedException();
        }

        public Task<IEnumerable<Recipe>> GetByName(string name, Task<IEnumerable<Recipe>> recipes)
        {
            throw new NotImplementedException();
        }

        public Task<IEnumerable<Recipe>> GetByRating(double RecipeRatingType, Task<IEnumerable<Recipe>> recipes)
        {
            throw new NotImplementedException();
        }

        public Task<IEnumerable<Recipe>> GetByServings(int servings, Task<IEnumerable<Recipe>> recipes)
        {
            throw new NotImplementedException();
        }

        public Task<IEnumerable<Recipe>> GetByVotes(int votes, Task<IEnumerable<Recipe>> recipes)
        {
            throw new NotImplementedException();
        }

        public Task<IEnumerable<Recipe>> SortBy(string sortCriteria, string sortType)
        {
            throw new NotImplementedException();
        }

        public Task UpdateCost()
        {
            throw new NotImplementedException();
        }
    }
}
