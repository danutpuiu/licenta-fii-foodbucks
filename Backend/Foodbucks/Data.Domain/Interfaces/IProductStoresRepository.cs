using Data.Domain.Entities.RecipeEntities;
using System;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace Data.Domain.Interfaces
{
    public interface IProductStoresRepository : IGenericRepository<ProductStore>
    {
        Task<ProductStore> GetByProductAndStore(Guid productId, Guid storeId);
        Task<IEnumerable<ProductStore>> GetByProduct(Guid productId);
        Task<IEnumerable<ProductStore>> GetByStore(Guid storeId);
        Task<ProductStore> GetCheapestStoreByProduct(Guid productId);
        Task UpdateAllPrices();
    }
}
