using System;
using System.Collections.Generic;
using System.Linq;

namespace zad3w1
{
    public class Population
    {
        private Random rng = new Random();
        private List<Creature> population;
        private readonly double mutationRate;
        private readonly bool elitism;
        public int generation = 1;

        public Population(int size, double mutationRate, bool elitism)
        {
            this.population = new List<Creature>();
            this.mutationRate = mutationRate;
            this.elitism = elitism;

            if (size < 2) // no point in creating population containing only one creature since selection algorithms won't work in such case
                size = 2;

            for (int i = 0; i < size; i++)
                this.population.Add(new Creature());
        }

        public void Evolve()
        {
            Population nextGen = new Population(this.population.Count(), this.mutationRate, this.elitism);

            // if elitism is active - rewrite fittest creature to next generation without any changes
            if (elitism)
                nextGen.population[0].SetGenotype(this.GetFittest().genotype);

            // select parents and produce offspring
            for (int i = (elitism ? 1 : 0); i < nextGen.population.Count(); i++)
            {
                Creature c1 = RouletteSelection();
                Creature c2 = RouletteSelection();
                while (c2 == c1)
                    c2 = RouletteSelection();

                /*Creature c1 = GetFittest(); // ranking selection
                Creature c2 = GetFittest2();
                if (c2 == c1)
                    throw new Exception();*/

                Creature c = Crossover(c1, c2);
                nextGen.population[i].SetGenotype(c.genotype);
            }

            // introduce mutations in new generation
            for (int i = (elitism ? 1 : 0); i < nextGen.population.Count(); i++)
                nextGen.Mutate(nextGen.population[i]);

            this.population = nextGen.population;
            generation++;
        }

        public Creature GetFittest()
        {
            Creature fittest = population[0];

            foreach (var creature in population)
                if (creature.fitness > fittest.fitness)
                    fittest = creature;

            return fittest;
        }

        public Creature GetFittest2()
        {
            Creature fittest1 = population[0];
            Creature fittest2 = population[1];

            foreach (var creature in population)
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
            Creature fattest = population[0];

            foreach (var creature in population)
                if (creature.fitness < fattest.fitness)
                    fattest = creature;

            return fattest;
        }

        public Creature RouletteSelection()
        {
            double fitnessSum = 0;
            foreach (var creature in population)
                fitnessSum += creature.fitness;

            double rand = population[0].Random01(0, 1) * fitnessSum;

            foreach (var creature in population)
            {
                rand -= creature.fitness;
                if (rand < 0)
                    return creature;
            }

            return population.Last();
        }

        public void Mutate(Creature c)
        {
            if (c.Random01(0, 1) <= mutationRate) // chances for mutation
            {
                //if (c.Random01(0, 1) <= 0.5) // kinda more realistic but much less efficient
                    c.genotype[0] += RandomGaussian();
                //else
                    c.genotype[1] += RandomGaussian();
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

            return c0;
        }

        public void CountFitness(Creature c)
        {
            c.fitness = 1 / Function(c.genotype[0], c.genotype[1]);
        }

        public void CountFitness()
        {
            foreach (var creature in population)
                CountFitness(creature);
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
