using Data.Domain.Entities.RecipeEntities;
using Data.Domain.Interfaces;
using Data.Persistence;
using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using System.Linq;
using Microsoft.EntityFrameworkCore;

namespace Logic
{
    public class ProductStoresRepository : GenericRepository<ProductStore>, IProductStoresRepository
    {
        private readonly IDatabaseContext _databaseContext;

        public ProductStoresRepository(IDatabaseContext databaseContext) : base(databaseContext)
        {
            _databaseContext = databaseContext;
        }

        public async Task<IEnumerable<ProductStore>> GetByProduct(Guid productId)
        {
            return await _databaseContext.ProductStores.Where(ps =>
                ps.ProductId == productId).ToListAsync();
        }

        public async Task<ProductStore> GetByProductAndStore(Guid productId, Guid storeId)
        {
            return await _databaseContext.ProductStores.Where(ps =>
                ps.ProductId == productId &&
                ps.StoreId == storeId).FirstOrDefaultAsync();
        }

        public async Task<IEnumerable<ProductStore>> GetByStore(Guid storeId)
        {
            return await _databaseContext.ProductStores.Where(ps =>
                ps.StoreId == storeId).ToListAsync();
        }

        public async Task<ProductStore> GetCheapestStoreByProduct(Guid productId)
        {
            return (await GetByProduct(productId)).OrderBy(r => r.Price).FirstOrDefault();
        }

        public async Task UpdateAllPrices()
        {
            throw new NotImplementedException();
        }
    }
}
