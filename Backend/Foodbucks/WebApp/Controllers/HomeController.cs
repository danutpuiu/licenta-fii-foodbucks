using Data.Domain.Interfaces;
using Microsoft.AspNetCore.Mvc;
using System.Diagnostics;
using System.Threading.Tasks;
using WebApp.DTO;
using WebApp.Models;

namespace WebApp.Controllers
{
    public class HomeController : Controller
    {
        private readonly IRecipesRepository _recipesRepository;
        
        public HomeController(IRecipesRepository recipesRepository)
        {
            _recipesRepository = recipesRepository;
        }

        [HttpGet]
        public async Task<IActionResult> Index()
        {
            HomepageDTO homepageDTO = new HomepageDTO();
            homepageDTO.Recipes = await _recipesRepository.GetAll();

            return Ok(homepageDTO);
        }

        [HttpGet]
        [Route("error")]
        public IActionResult Error()
        {
            return Ok(new ErrorViewModel { RequestId = Activity.Current?.Id ?? HttpContext.TraceIdentifier });
        }
    }
}