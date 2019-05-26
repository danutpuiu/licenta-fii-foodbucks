using Data.Domain.Entities.RecipeEntities;
using System;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace Data.Domain.Interfaces
{
    public interface IInstructionsRepository : IGenericRepository<Instruction>
    {
        Task<IEnumerable<Instruction>> GetByRecipe(Guid recipeId);
        Task<Instruction> GetByRecipeAndInstructionNr(Guid recipeId, int instructionNr);
    }
}
