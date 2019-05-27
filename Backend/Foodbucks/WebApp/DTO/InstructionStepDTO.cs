using System;
using System.ComponentModel.DataAnnotations;

namespace WebApp.DTO
{
    public class InstructionStepDTO
    {
        [Required, MaxLength(100000)]
        public String Description { get; set; }
        [Required]
        public int InstructionNr { get; set; }
    }
}
