using Data.Domain.Entities.RecipeEntities;
using Data.Domain.Interfaces;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using WebApp.DTO;

namespace WebApp.Controllers
{
    [Route("[controller]")]
    public class StoresController : Controller
    {
        private readonly IProductStoresRepository _productStoresRepository;
        private readonly IProductsRepository _productsRepository;
        private readonly IStoresRepository _storesRepository;
        
        public StoresController(IProductStoresRepository productStoresRepository,
            IProductsRepository productsRepository,
            IStoresRepository storesRepository)
        {
            _productsRepository = productsRepository;
            _productStoresRepository = productStoresRepository;
            _storesRepository = storesRepository;
        }

        [HttpPost]
        public async Task<IActionResult> Create([FromBody] StoreDTO storeDTO)
        {
            var store = Store.Create(storeDTO.Name,
                storeDTO.Description,
                storeDTO.Website);
            await _storesRepository.Add(store);
            return Ok(store);
        }

        [HttpGet]
        [Route("All")]
        public async Task<IActionResult> GetAll()
        {
            return Ok(await _storesRepository.GetAll());
        }

        [HttpGet]
        public async Task<IActionResult> Get(string name)
        {
            return Ok(await _storesRepository.GetByName(name));
        }

        [HttpGet("[action]")]
        public async Task<IActionResult> StoreDetails(Guid id)
        {
            StoreDTO storeDTO = new StoreDTO();

            Store store = await _storesRepository.GetById(id);

            storeDTO.Name = store.Name;
            storeDTO.Description = store.Description;
            storeDTO.Website = store.Website;

            List<ProductDTO> productDTOs = new List<ProductDTO>();
            IEnumerable<ProductStore> productStores = await _productStoresRepository.GetByStore(id);

            foreach(var productStore in productStores)
            {
                Product product = await _productsRepository.GetById(productStore.ProductId);
                ProductDTO productDTO = new ProductDTO
                {
                    Name = product.Name,
                    Brand = product.Brand,
                    Quantity = product.Quantity,
                    UnitOfMeasurement = product.UnitOfMeasurement
                };
                productDTOs.Add(productDTO);
            }

            storeDTO.Products = productDTOs;

            return Ok(storeDTO);

        }


    }
}