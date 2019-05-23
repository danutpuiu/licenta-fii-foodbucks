using Data.Domain.Entities.RecipeEntities;
using Data.Domain.Interfaces;
using Data.Persistence;
using System;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace Logic
{
    public class StoresRepository : GenericRepository<Store>, IStoresRepository
    {
        private readonly IDatabaseContext _databaseContext;

        public StoresRepository(IDatabaseContext databaseContext) : base(databaseContext)
        {
            _databaseContext = databaseContext;
        }

        public Task Add(Store entity)
        {
            throw new NotImplementedException();
        }

        public Task Edit(Store entity)
        {
            throw new NotImplementedException();
        }

        public Task<bool> Exists(string name)
        {
            throw new NotImplementedException();
        }

        public Task<Store> GetByName(string name)
        {
            throw new NotImplementedException();
        }

        Task<IEnumerable<Store>> IGenericRepository<Store>.GetAll()
        {
            throw new NotImplementedException();
        }

        Task<Store> IGenericRepository<Store>.GetById(Guid id)
        {
            throw new NotImplementedException();
        }
    }
}
