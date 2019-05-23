using Data.Domain.Entities.RecipeEntities;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace Data.Domain.Interfaces
{
    public interface IProductsRepository : IGenericRepository<Product>
    {
        Task AddProductCustom(string name, string brand, double quantity, string unitOfMeasurement);
        Task<IEnumerable<Product>> GetByName(string name);
        Task<Product> GetByNameAndBrand(string name, string brand);
    }
}
