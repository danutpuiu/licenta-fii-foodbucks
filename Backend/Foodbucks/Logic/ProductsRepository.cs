using Data.Domain.Entities.RecipeEntities;
using Data.Domain.Interfaces;
using Data.Persistence;
using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using System.Linq;

namespace Logic
{
    public class ProductsRepository : GenericRepository<Product>, IProductsRepository
    {

        private readonly IDatabaseContext _databaseContext;
        private readonly IStoresRepository _storesRepository;
        private readonly IProductStoresRepository _productStoresRepository;

        enum MeasureSystem
        {
            Undefined = 0,
            Mass = 1,
            Volume = 2,
            ByPiece = 3
        }

        public static Dictionary<string, Tuple<double, int>> unitOfMeasurementConversion = new Dictionary<string, Tuple<double, int>>
        {
            {"millilitre", new Tuple<double, int>(1.0, (int)MeasureSystem.Volume) },
            {"fluid ounce", new Tuple<double, int>(29.6, (int)MeasureSystem.Volume)},
            {"pint", new Tuple<double, int>(473.1, (int)MeasureSystem.Volume)},
            {"gallon", new Tuple<double, int>(3.79, (int)MeasureSystem.Volume)},
            {"cup", new Tuple<double, int>(16.0, (int)MeasureSystem.Volume)},
            {"quart", new Tuple<double, int>(946.353, (int)MeasureSystem.Volume)},
            {"litre", new Tuple<double, int>(1000.0, (int)MeasureSystem.Volume)},

            {"gram", new Tuple<double, int>(1.0, (int)MeasureSystem.Mass)},
            {"ounce", new Tuple<double, int>(28.35, (int)MeasureSystem.Mass)},
            {"kilogram", new Tuple<double, int>(1000.0, (int)MeasureSystem.Mass)},
            {"pound", new Tuple<double, int>(453.592, (int)MeasureSystem.Mass)},

            {"package", new Tuple<double, int>(1.0, (int)MeasureSystem.ByPiece)},
            {"slice", new Tuple<double, int>(0.2, (int)MeasureSystem.ByPiece)},
            {"chunk", new Tuple<double, int>(0.1, (int)MeasureSystem.ByPiece)},
            {"bunch", new Tuple<double, int>(5, (int)MeasureSystem.ByPiece)},
            {"bottle", new Tuple<double, int>(1, (int)MeasureSystem.ByPiece)},
            {"piece", new Tuple<double, int>(1.0, (int)MeasureSystem.ByPiece)},
            {"container", new Tuple<double, int>(1.0, (int)MeasureSystem.ByPiece)},
            {"box", new Tuple<double, int>(1.0, (int)MeasureSystem.ByPiece)},
            {"stalk", new Tuple<double, int>(0.1, (int)MeasureSystem.ByPiece)},
            {"can", new Tuple<double, int>(0.3, (int)MeasureSystem.ByPiece)},
            {"ball", new Tuple<double, int>(0.1, (int)MeasureSystem.ByPiece)},
            {"pack", new Tuple<double, int>(1.0, (int)MeasureSystem.ByPiece)},
            {"to taste", new Tuple<double, int>(1.0, (int)MeasureSystem.ByPiece)}
        };

        
        public ProductsRepository(IDatabaseContext databaseContext, IStoresRepository storesRepository, IProductStoresRepository productStoresRepository) : base(databaseContext)
        {
            _databaseContext = databaseContext;
            _storesRepository = storesRepository;
            _productStoresRepository = productStoresRepository;
        }
        
        public async Task AddProductCustom(string name, string brand, double quantity, string unitOfMeasurement, string storeName, double price)
        {
            Product product;
            if(!await Exists(name, brand, quantity, unitOfMeasurement))
            {
                Guid storeId;
                if (!await _storesRepository.Exists(storeName))
                {
                    Store store = Store.Create(storeName, "", "");
                    await _storesRepository.Add(store);
                    storeId = store.Id;
                }
                else
                {
                    storeId = (await _storesRepository.GetByName(storeName)).FirstOrDefault().Id;
                }

                int measurementSystem = (int)MeasureSystem.Undefined;
                if (unitOfMeasurementConversion.ContainsKey(unitOfMeasurement))
                {
                    measurementSystem = unitOfMeasurementConversion[unitOfMeasurement].Item2;
                }
                else
                {
                    measurementSystem = (int)MeasureSystem.Undefined;
                }

                product = Product.Create(name, brand, quantity, unitOfMeasurement, measurementSystem);
                await Add(product);

                Guid productId = product.Id;
                ProductStore productStore;
                productStore = ProductStore.Create(productId, storeId, price);
                await _productStoresRepository.Add(productStore);
            }
        }

        public async Task<IEnumerable<Product>> GetByName(string name)
        {
            return await _databaseContext.Products.Where(prod =>
                prod.Name.ToLower().Equals(name.ToLower())).ToListAsync();
        }

        public async Task<IEnumerable<Product>> GetByNameAndSystemOfMeasurement(string name, int systemOfMeasurement)
        {
            return await _databaseContext.Products.Where(prod =>
                prod.Name.ToLower().Equals(name.ToLower()) &&
                prod.MeasurementSystem == systemOfMeasurement).ToListAsync();
        }

        public async Task<IEnumerable<Product>> GetByNameAndBrand(string name, string brand)
        {
            return await _databaseContext.Products.Where(prod =>
                prod.Name.ToLower().Equals(name.ToLower()) &&
                prod.Brand.ToLower().Equals(brand.ToLower())).ToListAsync();
        }

        public async Task<bool> Exists(string name, string brand, double quantity, string unitOfMeasurement)
        {
            return await _databaseContext.Products.AnyAsync(
                product => 
                product.Name.ToLower().Equals(name.ToLower()) &&
                product.Brand.ToLower().Equals(brand.ToLower()) &&
                product.Quantity == quantity &&
                product.UnitOfMeasurement.ToLower().Equals(unitOfMeasurement.ToLower()));
        }

        public async Task<bool> Exists(string name)
        {
            return await _databaseContext.Products.AnyAsync(
                product =>
                product.Name.ToLower().Equals(name.ToLower()));
        }

        /* This function should compute the best product to be chosen */
        /* It should take into consideration to buy the lowest quantity necessary for the lowest price */
        public async Task<Product> GetCheapest(string name, double quantity, string unitOfMeasurement)
        {
            Product cheapestProduct = null;
            if (await Exists(name))
            {
                double valueCoefficient = 0;

                IEnumerable<Product> products = await GetByName(name);
                foreach (var product in products)
                {
                    IEnumerable <ProductStore> productStores = await _productStoresRepository.GetByProduct(product.Id);
                    double tempCoefficient;
                    /* Get the cheapest version of the same exact product from all stores */
                    tempCoefficient = product.Quantity / productStores.Min(r => r.Price);
                    /* Compute its value */
                    if (tempCoefficient > valueCoefficient)
                    {
                        valueCoefficient = tempCoefficient;
                        cheapestProduct = product;
                    }
                }
            }

            return cheapestProduct;
        }

        public bool CanBeConverted(string unitOfMeasure1, string unitOfMeasure2)
        {
            bool canBeConverted = false;

            if ((unitOfMeasurementConversion.ContainsKey(unitOfMeasure1) && unitOfMeasurementConversion.ContainsKey(unitOfMeasure2)) &&
                ((unitOfMeasurementConversion[unitOfMeasure1].Item2 == unitOfMeasurementConversion[unitOfMeasure2].Item1) ||
                unitOfMeasurementConversion[unitOfMeasure1].Item2 == (int)MeasureSystem.ByPiece ||
                unitOfMeasurementConversion[unitOfMeasure1].Item2 == (int)MeasureSystem.ByPiece ||
                unitOfMeasurementConversion[unitOfMeasure1].Item2 == (int)MeasureSystem.Undefined ||
                unitOfMeasurementConversion[unitOfMeasure1].Item2 == (int)MeasureSystem.Undefined))
            {
                
                canBeConverted = true;
            }


            return canBeConverted;
        }

        public double ConvertUnits(string unitOfMeasure1, string unitOfMeasure2, double valueToBeConverted)
        {
            if (!unitOfMeasurementConversion.ContainsKey(unitOfMeasure1) || !unitOfMeasurementConversion.ContainsKey(unitOfMeasure2) || !CanBeConverted(unitOfMeasure1, unitOfMeasure2))
            {
                return 0.0;
            }

            double convertedValue = 1.0;

            if (unitOfMeasurementConversion[unitOfMeasure1].Item2 == unitOfMeasurementConversion[unitOfMeasure2].Item2)
            {
                /* Convert first to the unit (gram, ml, package) then divide by the unit to be converted to */
                if(unitOfMeasurementConversion[unitOfMeasure2].Item1 != 0)
                    convertedValue = valueToBeConverted * unitOfMeasurementConversion[unitOfMeasure1].Item1 / unitOfMeasurementConversion[unitOfMeasure2].Item1;
            }

            return convertedValue;
        }
    }
}
