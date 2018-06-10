using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace zad3w1
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WindowHeight *= 3;
            Console.WindowHeight /= 2;

            // make population
            Population p = new Population(100, 0.5, true);

        // first generation
            double fit = 0;
            p.CountFitness();
            Console.WriteLine("\nGEN\t:  FITTEST'S FITNESS\t|  COORDINATES\t\t=  FUNCTION VALUE\n");
            Console.WriteLine(p.generation.ToString() + "\t:  " + p.GetFittest().fitness.ToString("N15") +
                              "\t|  " + p.GetFittest().Genotype2string() +
                              "\t=  " + p.Function(p.GetFittest().genotype[0], p.GetFittest().genotype[1]));

        // evolution
            while (p.GetFittest().fitness < 0.099 && p.generation < 10000)
            {
                p.Evolve();
                p.CountFitness();
                if (p.GetFittest().fitness > fit)
                {
                    fit = p.GetFittest().fitness;
                    Console.WriteLine(p.generation.ToString() + "\t:  " + p.GetFittest().fitness.ToString("N15 D3") +
                                      "\t|  " + p.GetFittest().Genotype2string() +
                                      "\t=  " + p.Function(p.GetFittest().genotype[0], p.GetFittest().genotype[1]));
                }
                else if(p.generation % 100 == 0)
                {
                    Console.WriteLine(" " + p.generation.ToString() + "\t:  " + p.GetFittest().fitness.ToString("N15") +
                                      "\t|  " + p.GetFittest().Genotype2string() +
                                      "\t=  " + p.Function(p.GetFittest().genotype[0], p.GetFittest().genotype[1]));
                }
            }

        // finish
            Console.WriteLine("\nDone");
            Console.Beep();
            Console.ReadKey();
        }
    }
}
