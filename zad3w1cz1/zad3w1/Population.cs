using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace zad3w1
{
    public class Population
    {
        private Random rng = new Random();
        public List<Creature> population;
        public int generation = 1;
        private readonly double mutationRate;
        private readonly bool elitism;

        public Population(int size, double mutationRate, bool elitism)
        {
            this.population = new List<Creature>();
            this.mutationRate = mutationRate;
            this.elitism = elitism;

            for (int i = 0; i < size; i++)
                this.population.Add( new Creature() );
        }

        public void Evolve()
        {
            Population nextGen = new Population( this.population.Count(), this.mutationRate, this.elitism );

            if(elitism)
                nextGen.population[0].SetGenotype( this.GetFittest().genotype );

            for (int i = (elitism ? 1 : 0); i < nextGen.population.Count(); i++)
            {
                Creature c1 = this.RouletteSelection();
                Creature c2 = this.RouletteSelection();
                while (c2 == c1)
                    c2 = this.RouletteSelection();

                /*Creature c1 = GetFittest(); // ranking selection? somehow manages to work worse than roulette
                Creature c2 = GetFittest2();
                if (c2 == c1)
                    throw new Exception();*/

                Creature c = Crossover( c1, c2 );
                nextGen.population[i].SetGenotype( c.genotype );
            }

            for (int i = (elitism ? 1 : 0); i < nextGen.population.Count(); i++)
            {
                nextGen.Mutate( nextGen.population[i] );
            }

            this.population = nextGen.population;
            this.generation++;
        }

        public Creature GetFittest()
        {
            Creature fittest = this.population[0];

            foreach (var creature in this.population)
                if (creature.fitness > fittest.fitness)
                    fittest = creature;

            return fittest;
        }

        public Creature GetFittest2()
        {
            Creature fittest1 = this.population[0];
            Creature fittest2 = this.population[1];

            foreach (var creature in this.population)
            {
                if (creature.fitness > fittest1.fitness)
                {
                    fittest2 = fittest1;
                    fittest1 = creature;
                }
                /*else if (creature.fitness > fittest2.fitness)
                {
                    fittest2 = creature;
                }*/
            }

            return fittest2;
        }

        public Creature GetFattest()
        {
            Creature fattest = this.population[0];

            foreach (var creature in this.population)
                if (creature.fitness < fattest.fitness)
                    fattest = creature;

            return fattest;
        }

        public Creature RouletteSelection()
        {
            double fitnessSum = 0;
            foreach (var c in this.population)
                fitnessSum += c.fitness;

            double rand = this.population[0].Random01(0, 1) * fitnessSum;

            foreach (var c in this.population)
            {
                rand -= c.fitness;
                if (rand < 0)
                    return c;
            }

            return this.population.Last();
            //throw new Exception();
        }

        public void Mutate(Creature c)
        {
            if (c.Random01(0, 1) <= mutationRate) // chances for mutation
            {
                if(c.Random01(0, 1) <= 0.5) // 50% chances to mutate each gene
                {
                    //double x = c.Random01(0, 18);

                    /*while ( !c.Constraint1(x, c.genotype[1]) || !c.Constraint2(x, c.genotype[1]) ) // we can't use constraints since they won't let Y move below ~7
                        x = c.Random01(0, 18);*/

                    //c.genotype[0] = x;

                    c.genotype[0] += RandomGaussian();
                }
                else
                {
                    //double y = c.Random01(0, 15);

                    /*while ( !c.Constraint1(c.genotype[0], y) || !c.Constraint2(c.genotype[0], y) )
                        y = c.Random01(0, 15);*/

                    //c.genotype[1] = y;

                    c.genotype[1] += RandomGaussian();
                }
            }
        }

        public Creature Crossover(Creature c1, Creature c2)
        {
            Creature c0 = new Creature();

            for (int i = 0; i < c0.genotype.Count(); i++)
            {
                if (c0.Random01(0, 1) <= 0.5) // 50% chances to inherit each gene from each parent
                    c0.genotype[i] = c1.genotype[i];
                else
                    c0.genotype[i] = c2.genotype[i];
            }

            /*c0.genotype[0] = (c1.genotype[0] + c2.genotype[0]) / 2; // taking average feels less efficient and is unrealistic
            c0.genotype[1] = (c1.genotype[1] + c2.genotype[1]) / 2;*/

            return c0;
        }

        public void CountFitness(Creature c)
        {
            c.fitness = 1/Function( c.genotype[0], c.genotype[1] );
        }

        public void CountFitness()
        {
            foreach (var c in this.population)
                this.CountFitness(c);
        }

        public double RandomGaussian(double sigma = 1, double mu = 0)
        {
            double u1 = 1.0 - rng.NextDouble(); // = (0; 1]
            double u2 = 1.0 - rng.NextDouble();

            double rand_std_normal = Math.Sqrt(-2.0 * Math.Log(u1)) * Math.Sin(2.0 * Math.PI * u2);

            return mu + sigma * rand_std_normal;
        }

        public double Function(double x, double y) => 100 * Math.Pow((x * x - y), 2) + Math.Pow((1 - x), 2) + 10;
    }
}
