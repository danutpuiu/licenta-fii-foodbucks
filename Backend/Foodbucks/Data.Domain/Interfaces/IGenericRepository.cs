using System;
using System.Collections.Generic;
using System.Threading.Tasks;

// Source: Danut Puiu, Cristian Rusu, Nicusor Turcu, Tamara Trifan https://github.com/turcunicusor/KunFooD

namespace Data.Domain.Interfaces
{
    public interface IGenericRepository<T> where T : class
    {
        Task<IEnumerable<T>> GetAll();
        Task<T> GetById(Guid id);
        Task Add(T entity);
        Task Edit(T entity);
        Task Save();
        Task Delete(Guid id);
    }
}
