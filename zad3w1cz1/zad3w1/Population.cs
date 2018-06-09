using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace zad3w1
{
    public class Population
    {
        public List<Creature> population;
        public int generation = 0;
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
                nextGen.population[0].SetGenotype(nextGen.GetFittest().genotype );

            for (int i = (elitism ? 1 : 0); i < nextGen.population.Count(); i++)
            {
                Creature c = Crossover( this.RouletteSelection(), this.RouletteSelection() );
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

            double rand = new Creature().Random01(0, 1) * fitnessSum;

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
                    double x = c.Random01(0, 18);

                    while ( !c.Constraint1(x, c.genotype[1]) || !c.Constraint2(x, c.genotype[1]) )
                        x = c.Random01(0, 18);

                    c.genotype[0] = x;
                }
                else
                {
                    double y = c.Random01(0, 15);

                    while ( !c.Constraint1(c.genotype[0], y) || !c.Constraint2(c.genotype[0], y) )
                        y = c.Random01(0, 15);

                    c.genotype[1] = y;
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

            /*c0.genotype[0] = (c1.genotype[0] + c2.genotype[0]) / 2;
            c0.genotype[1] = (c1.genotype[1] + c2.genotype[1]) / 2;*/

            return c0;
        }

        public void CountFitness(Creature c)
        {
            // fitness is counted as distance from Z_max = Function(18, 0) = 10 497 899
            //double z = 10497899;
            c.fitness = 1/Function( c.genotype[0], c.genotype[1] );
        }

        public void CountFitness()
        {
            foreach (var c in this.population)
                this.CountFitness(c);
        }

        public double Function(double x, double y) => 100 * Math.Pow((x * x - y), 2) + Math.Pow((1 - x), 2) + 10;
    }
}
