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

        public static Instruction Create(Guid recipeId, string description)
        {
            var instance = new Instruction { Id = Guid.NewGuid() };
            instance.Update(recipeId, description);
            return instance;
        }

        public void Update(Guid recipeId, string description)
        {
            RecipeId = recipeId;
            Description = description;
        }
    }
}
