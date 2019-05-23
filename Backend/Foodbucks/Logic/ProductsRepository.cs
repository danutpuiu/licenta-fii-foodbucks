using Data.Domain.Entities.RecipeEntities;
using Data.Domain.Interfaces;
using Data.Persistence;
using System;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace Logic
{
    public class ProductsRepository : GenericRepository<Product>, IProductsRepository
    {
        private readonly IDatabaseContext _databaseContext;
        private readonly IStoresRepository _storesRepository;
        private readonly IProductStoresRepository _productStoresRepository;

        public ProductsRepository(IDatabaseContext databaseContext, IStoresRepository storesRepository, IProductStoresRepository productStoresRepository) : base(databaseContext)
        {
            _databaseContext = databaseContext;
            _storesRepository = storesRepository;
            _productStoresRepository = productStoresRepository;
        }

        public Task AddProductCustom(string name, string brand, double quantity, string unitOfMeasurement)
        {
            throw new NotImplementedException();
        }

        public Task<IEnumerable<Product>> GetByName(string name)
        {
            throw new NotImplementedException();
        }

        public Task<Product> GetByNameAndBrand(string name, string brand)
        {
            throw new NotImplementedException();
        }
    }
}
