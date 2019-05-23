using Data.Domain.Entities.RecipeEntities;
using Data.Domain.Interfaces;
using Data.Persistence;
using System;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace Logic
{
    public class InstructionsRepository : GenericRepository<Instruction>, IInstructionsRepository
    {
        private readonly IDatabaseContext _databaseContext;

        public InstructionsRepository(IDatabaseContext databaseContext) : base(databaseContext)
        {
            _databaseContext = databaseContext;
        }

        public Task<IEnumerable<Instruction>> GetByRecipe(Guid recipeId)
        {
            throw new NotImplementedException();
        }

        public Task<IEnumerable<Instruction>> GetByRecipeAndInstructionNr(Guid recipeId, int instructionNr)
        {
            throw new NotImplementedException();
        }
    }
}
