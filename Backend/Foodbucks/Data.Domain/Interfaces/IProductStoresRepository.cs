using Data.Domain.Entities.RecipeEntities;
using System;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace Data.Domain.Interfaces
{
    public interface IProductStoresRepository : IGenericRepository<ProductStore>
    {
        Task<ProductStore> Get(Guid productId, Guid recipeId);
        Task Delete(Guid productId, Guid recipeId);
        Task<IEnumerable<ProductStore>> GetByProduct(Guid productId);
        Task<IEnumerable<ProductStore>> GetByStore(Guid storeId);
        Task UpdateAllPrices();
    }
}
