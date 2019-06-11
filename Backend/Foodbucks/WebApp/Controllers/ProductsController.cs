using Data.Domain.Entities.RecipeEntities;
using Data.Domain.Interfaces;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using WebApp.DTO;

namespace WebApp.Controllers
{
    [Route("[controller]")]
    public class ProductsController : Controller
    {
        private readonly IProductStoresRepository _productStoresRepository;
        private readonly IProductsRepository _productsRepository;
        private readonly IStoresRepository _storesRepository;

        public ProductsController(IProductStoresRepository productStoresRepository,
            IProductsRepository productsRepository,
            IStoresRepository storesRepository)
        {
            _productsRepository = productsRepository;
            _productStoresRepository = productStoresRepository;
            _storesRepository = storesRepository;
        }

        [HttpPost]
        public async Task<IActionResult> Create([FromBody] ProductDTO productDTO)
        {
            var systemOfMeasurement = await _productsRepository.AssignMeasurementUnitToMeasurementSystem(productDTO.UnitOfMeasurement);

            var product = Product.Create(
                productDTO.Name,
                productDTO.Brand,
                productDTO.Quantity,
                productDTO.UnitOfMeasurement,
                systemOfMeasurement);
            await _productsRepository.Add(product);

            foreach(var price in productDTO.Prices)
            {
                var store = await _storesRepository.GetByName(price.StoreName);
                var productStore = ProductStore.Create(
                    product.Id,
                    store.FirstOrDefault().Id,
                    price.Price);
                await _productStoresRepository.Add(productStore);
            }

            return Ok(product);
        }

        [HttpGet]
        [Route("All")]
        public async Task<IActionResult> GetAll()
        {
            return Ok(await _productsRepository.GetAll());
        }

        [HttpGet]
        public async Task<IActionResult> Get(string name)
        {
            return Ok(await _productsRepository.GetByName(name));
        }
        
        [HttpGet("[action]")]
        public async Task<IActionResult> ProductPrices(Guid id)
        {
            return Ok(await _productStoresRepository.GetByProduct(id));
        }

        [HttpGet("[action]")]
        public async Task<IActionResult> ProductDetails(Guid id)
        {
            ProductDTO productDTO = new ProductDTO();

            Product product = await _productsRepository.GetById(id);
            IEnumerable<ProductStore> productStore = await _productStoresRepository.GetByProduct(product.Id);
             

            productDTO.Name = product.Name;
            productDTO.Quantity = product.Quantity;
            productDTO.Brand = product.Brand;
            productDTO.UnitOfMeasurement = product.UnitOfMeasurement;

            List<PriceInfoDTO> prices = new List<PriceInfoDTO>();

            foreach(var price in productStore)
            {
                Store store = await _storesRepository.GetById(price.StoreId);
                PriceInfoDTO priceInfoDTO = new PriceInfoDTO()
                {
                    Price = price.Price,
                    StoreName = store.Name
                };
                prices.Add(priceInfoDTO);
            }
            prices.OrderBy(x => x.Price);
            productDTO.Prices = prices;

            return Ok(productDTO);
        }
    }
}