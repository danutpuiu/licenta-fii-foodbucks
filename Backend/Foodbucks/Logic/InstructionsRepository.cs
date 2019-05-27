using Data.Domain.Entities.RecipeEntities;
using Data.Domain.Interfaces;
using Data.Persistence;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
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

        public async Task AddInstruction(Guid recipeId, string description, int instructionNr)
        {
            Instruction instruction;

            instruction = Instruction.Create(recipeId, description, instructionNr);
            await Add(instruction);

        }

        public async Task<IEnumerable<Instruction>> GetByRecipe(Guid recipeId)
        {
            return await _databaseContext.Instructions.Where(i =>
                i.RecipeId == recipeId).ToListAsync();
        }

        public async Task<Instruction> GetByRecipeAndInstructionNr(Guid recipeId, int instructionNr)
        {
            return _databaseContext.Instructions.Where(i =>
                i.RecipeId == recipeId &&
                i.InstructionNr == instructionNr).FirstOrDefault();
        }
    }
}
