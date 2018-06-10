using System;

namespace zad3w1
{
    class Program
    {
        static Population population;

        static void Main(string[] args)
        {
            Console.WindowHeight *= 3;
            Console.WindowHeight /= 2;

        // set up and start population
            int    populationSize = 100;
            double mutationRate   = 5;
            bool   elitism        = true;
            double wantedFitness  = 0.1; // function minimum is 10, so this value shouldn't be higher than 0.1
            population = new Population(populationSize, mutationRate, elitism);

        // first generation
            double bestFitness = 0;
            population.CountFitness();
            Console.WriteLine("\nGEN\t:  FITTEST'S FITNESS\t|  COORDINATES\t\t=  FUNCTION VALUE\n");
            PrintStatus();

        // evolution
            while (population.GetFittest().fitness < wantedFitness && population.generation < 10000)
            {
                population.Evolve();
                population.CountFitness();

                if (population.GetFittest().fitness > bestFitness)
                {
                    bestFitness = population.GetFittest().fitness;
                    PrintStatus();
                }
                else if(population.generation % 100 == 0)
                {
                    Console.Write(" ");
                    PrintStatus();
                }
            }

        // finish
            Console.WriteLine("\nDone");
            Console.Beep();
            Console.ReadKey();
        }

        static private void PrintStatus()
        {
            Console.WriteLine(population.generation.ToString() + "\t:  " + population.GetFittest().fitness.ToString("N15") +
                              "\t|  " + population.GetFittest().Genotype2string() +
                              "\t=  " + population.Function(population.GetFittest().genotype[0], population.GetFittest().genotype[1]).ToString());
        }
    }
}
