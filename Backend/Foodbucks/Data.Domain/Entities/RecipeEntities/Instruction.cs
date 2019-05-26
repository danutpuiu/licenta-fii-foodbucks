using System;

namespace Data.Domain.Entities.RecipeEntities
{
    public class Instruction
    {
        private Instruction()
        {

        }
        public Guid Id { get; private set; }
        public Guid RecipeId { get; private set; }
        public string Description { get; private set; }
        public int InstructionNr { get; private set; }

        public static Instruction Create(Guid recipeId, string description, int instructionNr)
        {
            var instance = new Instruction { Id = Guid.NewGuid() };
            instance.Update(recipeId, description, instructionNr);
            return instance;
        }

        public void Update(Guid recipeId, string description, int instructionNr)
        {
            RecipeId = recipeId;
            Description = description;
            InstructionNr = instructionNr;
        }
    }
}
