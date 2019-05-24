using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using Data.Domain.Entities.RecipeEntities;
using Data.Domain.Interfaces;
using Data.Persistence;

namespace Logic
{
    public class IngredientsRepository : GenericRepository<Ingredient>, IIngredientsRepository
    {
        private readonly IDatabaseContext _databaseContext;
        private readonly IProductsRepository _productsRepository;

        

        public IngredientsRepository(IDatabaseContext databaseContext, IProductsRepository productsRepository) : base(databaseContext)
        {
            _databaseContext = databaseContext;
            _productsRepository = productsRepository;
        }

        public async Task AddIngredient(Guid recipeId,  string name, double quantity, string unitOfMeasurement)
        {
            Ingredient ingredient;
            Product product;

            if(! await _productsRepository.Exists(name))
            {
                product = null;
            }
            else
            {
                product = await _productsRepository.GetCheapest(name, quantity, unitOfMeasurement);
            }
        }

        public async Task<IEnumerable<Ingredient>> GetByName(string name)
        {
            throw new NotImplementedException();
        }

        public async Task<Ingredient> GetByNameAndUnitOfMeasure(string name, string unitOfMeasure)
        {
            throw new NotImplementedException();
        }

        public async Task<IEnumerable<Ingredient>> GetByRecipe(Guid recipeId)
        {
            throw new NotImplementedException();
        }

        public async Task UpdateCost()
        {
            throw new NotImplementedException();
        }
    }
}
