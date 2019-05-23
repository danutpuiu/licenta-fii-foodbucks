using Data.Domain.Entities.RecipeEntities;
using Data.Domain.Interfaces;
using Data.Persistence;
using System;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace Logic
{
    public class ProductStoresRepository : GenericRepository<ProductStore>, IProductStoresRepository
    {
        private readonly IDatabaseContext _databaseContext;

        public ProductStoresRepository(IDatabaseContext databaseContext) : base(databaseContext)
        {
            _databaseContext = databaseContext;
        }

        public Task Delete(Guid productId, Guid recipeId)
        {
            throw new NotImplementedException();
        }

        public Task<ProductStore> Get(Guid productId, Guid recipeId)
        {
            throw new NotImplementedException();
        }

        public Task<IEnumerable<ProductStore>> GetByProduct(Guid productId)
        {
            throw new NotImplementedException();
        }

        public Task<IEnumerable<ProductStore>> GetByStore(Guid storeId)
        {
            throw new NotImplementedException();
        }

        public Task UpdateAllPrices()
        {
            throw new NotImplementedException();
        }
    }
}
