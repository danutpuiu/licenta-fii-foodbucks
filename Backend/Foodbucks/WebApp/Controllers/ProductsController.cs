using Data.Domain.Entities.RecipeEntities;
using Data.Domain.Interfaces;
using Microsoft.AspNetCore.Mvc;
using System;
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

            var store = await _storesRepository.GetByName(productDTO.Store);
            var productStore = ProductStore.Create(
                product.Id,
                store.FirstOrDefault().Id,
                productDTO.Price);

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

            ProductStore productStore = await _productStoresRepository.GetById(id);
            Product product = await _productsRepository.GetById(productStore.ProductId);
            Store store = await _storesRepository.GetById(productStore.StoreId);
             

            productDTO.Name = product.Name;
            productDTO.Quantity = product.Quantity;
            productDTO.Brand = product.Brand;
            productDTO.UnitOfMeasurement = product.UnitOfMeasurement;
            productDTO.Store = store.Name;
            productDTO.Price = productStore.Price;
            

            return Ok(productDTO);
        }
    }
}