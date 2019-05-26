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
        private readonly IProductsRepository _productsRepository;

        public ProductStoresRepository(IDatabaseContext databaseContext, IProductsRepository productsRepository) : base(databaseContext)
        {
            _databaseContext = databaseContext;
            _productsRepository = productsRepository;
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

        public async Task<ProductStore> GetCheapestStoreByProduct(string name, double quantity, string unitOfMeasurement)
        {
            ProductStore cheapestProduct = null;
            if (await _productsRepository.Exists(name))
            {
                double valueCoefficient = 0;

                IEnumerable<Product> products = await _productsRepository.GetByName(name);
                foreach (var product in products)
                {
                    IEnumerable<ProductStore> productStores = await GetByProduct(product.Id);
                    double tempCoefficient;

                    ProductStore tempProductStore = productStores.OrderBy(r => r.Price).First();
                    /* Get the cheapest version of the same exact product from all stores */
                    tempCoefficient = product.Quantity / tempProductStore.Price;
                    /* Compute its value */
                    if (tempCoefficient > valueCoefficient)
                    {
                        valueCoefficient = tempCoefficient;
                        cheapestProduct = tempProductStore;
                    }
                }
            }

            return cheapestProduct;
        }

        public async Task UpdateAllPrices()
        {
            throw new NotImplementedException();
        }
    }
}
