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
    public class StoresRepository : GenericRepository<Store>, IStoresRepository
    {
        private readonly IDatabaseContext _databaseContext;

        public StoresRepository(IDatabaseContext databaseContext) : base(databaseContext)
        {
            _databaseContext = databaseContext;
        }

        public async Task<bool> Exists(string name)
        {
            return await _databaseContext.Stores.AnyAsync(
                store => store.Name.ToLower().Equals(name.ToLower()));
        }

        public async Task<IEnumerable<Store>> GetByName(string name)
        {
            return await _databaseContext.Stores.Where(prod =>
                prod.Name.ToLower().Equals(name.ToLower())).ToListAsync();
        }
    }
}
