using System;

namespace Data.Domain.Entities.RecipeEntities
{
    public class Ingredient
    {
        private Ingredient()
        {
        }

        public Guid Id { get; private set; }
        public Guid RecipeId { get; private set; }
        public Guid ProductStoreId { get; private set; }
        public int NrOfProductsNecessary { get; private set; }
        public string Name { get; private set; }
        public double Quantity { get; private set; }
        public string UnitOfMeasurement { get; private set; }
        public double Cost { get; private set; }

        public static Ingredient Create(Guid recipeId, Guid productStoreId, string name, double quantity, double cost, string unitOfMeasurement, int nrOfProductsNecessary)
        {
            var instance = new Ingredient { Id = Guid.NewGuid() };
            instance.Update(recipeId, productStoreId, name, quantity, cost, unitOfMeasurement, nrOfProductsNecessary);
            return instance;
        }

        public void Update(Guid recipeId, Guid productStoreId, string name, double quantity, double cost, string unitOfMeasurement, int nrOfProductsNecessary)
        {
            RecipeId = recipeId;
            ProductStoreId = productStoreId;
            Name = name;
            Quantity = quantity;
            Cost = cost;
            UnitOfMeasurement = unitOfMeasurement;
            NrOfProductsNecessary = nrOfProductsNecessary;
        }
    }
}
