using Data.Domain.Entities.RecipeEntities;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace Data.Domain.Interfaces
{
    public interface IProductsRepository : IGenericRepository<Product>
    {
        Task AddProductCustom(string name, string brand, double quantity, string unitOfMeasurement, string storeName, double price);
        Task<bool> Exists(string name, string brand, double quantity, string unitOfMeasurement);
        Task<bool> Exists(string name);
        Task<IEnumerable<Product>> GetByName(string name);
        Task<IEnumerable<Product>> GetByBrand(string name);
        Task<IEnumerable<Product>> GetByNameAndBrand(string name, string brand);
        Task<IEnumerable<Product>> GetByNameAndSystemOfMeasurement(string name, int systemOfMeasurement);
        Task<Product> GetCheapest(string name, double quantity, string unitOfMeasurement);
        bool CanBeConverted(string unitOfMeasure1, string unitOfMeasure2);
        double ConvertUnits(string unitOfMeasure1, string unitOfMeasure2, double valueToBeConverted);
        Task<int> AssignMeasurementUnitToMeasurementSystem(string MeasurementUnit);
    }
}
