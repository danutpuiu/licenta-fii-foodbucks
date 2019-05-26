using System;

namespace Data.Domain.Entities.RecipeEntities
{
    public class ProductStore
    {
        private ProductStore()
        {

        }

        public Guid Id { get; private set; }
        public Guid ProductId { get; private set; }
        public Guid StoreId { get; private set; }
        public double Price { get; private set; }

        public static ProductStore Create(Guid productId, Guid storeId, double price)
        {
            var instance = new ProductStore { Id = Guid.NewGuid() }; 
            instance.Update(productId, storeId, price);
            return instance;
        }

        public void Update(Guid productId, Guid storeId, double price)
        {
            ProductId = productId;
            StoreId = storeId;
            Price = price;
        }
    }
}
