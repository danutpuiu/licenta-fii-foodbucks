using Data.Domain.Entities.RecipeEntities;
using System;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace Data.Domain.Interfaces
{
    public interface IIngredientsRepository : IGenericRepository<Ingredient>
    {
        Task AddIngredient(Guid productId, string name, double quantity, double cost, string unitOfMeasurement);
        Task<IEnumerable<Ingredient>> GetByName(string name);
        Task<Ingredient> GetByNameAndUnitOfMeasure(string name, string unitOfMeasure);
        Task UpdateCost();
    }
}
