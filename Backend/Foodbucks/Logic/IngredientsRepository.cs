using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Data.Domain.Entities.RecipeEntities;
using Data.Domain.Interfaces;
using Data.Persistence;
using Microsoft.EntityFrameworkCore;

namespace Logic
{
    public class IngredientsRepository : GenericRepository<Ingredient>, IIngredientsRepository
    {
        private readonly IDatabaseContext _databaseContext;
        private readonly IProductsRepository _productsRepository;
        private readonly IProductStoresRepository _productStoresRepository;        

        public IngredientsRepository(IDatabaseContext databaseContext, IProductStoresRepository productStoresRepository, IProductsRepository productsRepository) : base(databaseContext)
        {
            _databaseContext = databaseContext;
            _productStoresRepository = productStoresRepository;
            _productsRepository = productsRepository;
        }

        public async Task AddIngredient(Guid recipeId,  string name, double quantity, string unitOfMeasurement)
        {
            Ingredient ingredient;

            ProductStore productStoreCheapest;
            int nrOfProductsNecessary = 0;
            double cost = 0;

            if (! await _productsRepository.Exists(name))
            {
                productStoreCheapest = null;
            }
            else
            {
                Product product = (await _productsRepository.GetByName(name)).FirstOrDefault();
                productStoreCheapest = await _productStoresRepository.GetCheapestStoreByProduct(product.Id);
            }

            if(productStoreCheapest != null)
            {
                Product product = await _productsRepository.GetById(productStoreCheapest.ProductId);
                double quantityAfterConversion = _productsRepository.ConvertUnits(unitOfMeasurement, product.UnitOfMeasurement, quantity);
                nrOfProductsNecessary = (int)Math.Round(quantityAfterConversion / product.Quantity) + 1;
                cost = nrOfProductsNecessary * productStoreCheapest.Price;
            }
            
            ingredient = Ingredient.Create(recipeId, productStoreCheapest.Id, name, quantity, cost, unitOfMeasurement, nrOfProductsNecessary);
            await Add(ingredient); 
        }

        public async Task<IEnumerable<Ingredient>> GetByName(string name)
        {
            return await _databaseContext.Ingredients.Where(prod =>
                prod.Name.ToLower().Equals(name.ToLower())).ToListAsync();
        }

        public async Task<IEnumerable<Ingredient>> GetByRecipe(Guid recipeId)
        {
            return await _databaseContext.Ingredients.Where(prod =>
                prod.RecipeId == recipeId).ToListAsync();
        }

        public async Task UpdateCost(Guid ingredientId)
        {
            Ingredient ingredient = _databaseContext.Ingredients.Where(i => i.Id == ingredientId).FirstOrDefault();
            ProductStore productStore = await _productStoresRepository.GetById(ingredient.ProductStoreId);
            double newCost = ingredient.NrOfProductsNecessary * productStore.Price;

            ingredient.Update(ingredient.RecipeId, ingredient.ProductStoreId , ingredient.Name, ingredient.Quantity, newCost, ingredient.UnitOfMeasurement, ingredient.NrOfProductsNecessary);
            _databaseContext.SaveChanges();
        }
    }
}
