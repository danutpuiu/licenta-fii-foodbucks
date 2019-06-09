﻿// <auto-generated />
using System;
using Data.Persistence;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Storage.ValueConversion;

namespace Data.Persistence.Migrations
{
    [DbContext(typeof(DatabaseContext))]
    partial class DatabaseContextModelSnapshot : ModelSnapshot
    {
        protected override void BuildModel(ModelBuilder modelBuilder)
        {
#pragma warning disable 612, 618
            modelBuilder
                .HasAnnotation("ProductVersion", "2.2.4-servicing-10062")
                .HasAnnotation("Relational:MaxIdentifierLength", 128)
                .HasAnnotation("SqlServer:ValueGenerationStrategy", SqlServerValueGenerationStrategy.IdentityColumn);

            modelBuilder.Entity("Data.Domain.Entities.RecipeEntities.Ingredient", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd();

                    b.Property<double>("Cost");

                    b.Property<string>("Name");

                    b.Property<int>("NrOfProductsNecessary");

                    b.Property<Guid>("ProductStoreId");

                    b.Property<double>("Quantity");

                    b.Property<Guid>("RecipeId");

                    b.Property<string>("UnitOfMeasurement");

                    b.HasKey("Id");

                    b.ToTable("Ingredients");
                });

            modelBuilder.Entity("Data.Domain.Entities.RecipeEntities.Instruction", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("Description");

                    b.Property<int>("InstructionNr");

                    b.Property<Guid>("RecipeId");

                    b.HasKey("Id");

                    b.ToTable("Instructions");
                });

            modelBuilder.Entity("Data.Domain.Entities.RecipeEntities.Product", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("Brand");

                    b.Property<int>("MeasurementSystem");

                    b.Property<string>("Name");

                    b.Property<double>("Quantity");

                    b.Property<string>("UnitOfMeasurement");

                    b.HasKey("Id");

                    b.ToTable("Products");
                });

            modelBuilder.Entity("Data.Domain.Entities.RecipeEntities.ProductStore", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd();

                    b.Property<double>("Price");

                    b.Property<Guid>("ProductId");

                    b.Property<Guid>("StoreId");

                    b.HasKey("Id");

                    b.ToTable("ProductStores");
                });

            modelBuilder.Entity("Data.Domain.Entities.RecipeEntities.Recipe", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd();

                    b.Property<int>("Calories");

                    b.Property<int>("CookingTime");

                    b.Property<double>("Cost");

                    b.Property<string>("Description");

                    b.Property<int>("Likes");

                    b.Property<string>("Name");

                    b.Property<double>("Rating");

                    b.Property<int>("Servings");

                    b.Property<int>("Votes");

                    b.HasKey("Id");

                    b.ToTable("Recipes");
                });

            modelBuilder.Entity("Data.Domain.Entities.RecipeEntities.Store", b =>
                {
                    b.Property<Guid>("Id")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("Description");

                    b.Property<string>("Name");

                    b.Property<string>("Website");

                    b.HasKey("Id");

                    b.ToTable("Stores");
                });
#pragma warning restore 612, 618
        }
    }
}
