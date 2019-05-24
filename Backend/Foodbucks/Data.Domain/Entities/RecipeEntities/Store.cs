using System;

namespace Data.Domain.Entities.RecipeEntities
{
    public class Store
    {
        private Store()
        {

        }

        public Guid Id { get; private set; }
        public string Name { get; private set; }
        public string Description { get; private set; }
        public string Website { get; private set; }

        public static Store Create(string name, string description, string website)
        {
            var instance = new Store { Id = Guid.NewGuid() };
            instance.Update(name, description, website);
            return instance;
        }

        public void Update(string name, string description, string website)
        {
            Name = name;
            Description = description;
            Website = website;
        }
    }
}
