using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using Data.Domain.Entities.RecipeEntities;
using Data.Domain.Interfaces;
using Data.Persistence;

namespace Logic
{
    public class IngredientsRepository : GenericRepository<Ingredient>, IIngredientsRepository
    {
        private readonly IDatabaseContext _databaseContext;
        private readonly IProductsRepository _productsRepository;

        public static Dictionary<string, double> unitOfMeasurementConversion = new Dictionary<string, double>
        {
             {"millilitre", 1.0},
             {"fluid ounce", 29.6},
             {"pint", 473.1},
             {"gallon", 3.79},
             {"cup", 16.0},
             {"quart", 946.353},
             {"litre", 1000.0},

             {"gram", 1.0},
             {"ounce", 28.35},
             {"kilogram", 1000.0},
             {"pound", 453.592},

             {"slice", 0.2},
             {"chunk", 0.1},
             {"bunch", 5},
             {"package", 1},
             {"bottle", 1},
             {"piece", 1},
             {"container", 1},
             {"box", 1},
             {"stalk", 0},
             {"can", 0.3},
             {"ball", 0.1},
             {"pack", 1},

             {"to taste", 0}
        };

        public IngredientsRepository(IDatabaseContext databaseContext, IProductsRepository productsRepository) : base(databaseContext)
        {
            _databaseContext = databaseContext;
            _productsRepository = productsRepository;
        }

        public Task AddIngredient(Guid productId, string name, double quantity, double cost, string unitOfMeasurement)
        {
            throw new NotImplementedException();
        }

        public Task<IEnumerable<Ingredient>> GetByName(string name)
        {
            throw new NotImplementedException();
        }

        public Task<Ingredient> GetByNameAndUnitOfMeasure(string name, string unitOfMeasure)
        {
            throw new NotImplementedException();
        }

        public Task UpdateCost()
        {
            throw new NotImplementedException();
        }
    }
}
