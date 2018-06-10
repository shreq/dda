using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Security.Cryptography;
using System.Threading;

namespace zad3w1
{
    public class Creature
    {
        private RandomNumberGenerator rng = new RNGCryptoServiceProvider();
        public double fitness = 0;
        public List<double> genotype;

        public Creature()
        {
            this.genotype = new List<double>();

            double x = Random01(0, 18); // 0 <= x <= 18
            double y = Random01(0, 15); // 0 <= y <= 15
            while ( !Constraint1(x, y) || !Constraint2(x, y) )
            {
                x = Random01(0, 18);
                y = Random01(0, 15);
            }

            genotype.Add(x);
            genotype.Add(y);
        }

        public void SetGenotype(List<double> newSet)
        {
            if (this.genotype.Count() != newSet.Count())
                throw new Exception();

            for (int i = 0; i < this.genotype.Count(); i++)
                this.genotype[i] = newSet[i];
            //this.fitness = 0;
        }

        public string Genotype2string()
        {
            string str = "";
            foreach (var g in this.genotype)
                str += g.ToString("N5") + "; ";
            return str;
        }

        public double Random01(double min, double max)
        {
            if (min > max)
                min = Interlocked.Exchange(ref max, min);
            else if (min == max)
                return min;

            min *= 1000000.0;
            max *= 1000000.0;

            var data = new byte[4];
            rng.GetBytes(data);

            double val = (double)Math.Abs(BitConverter.ToInt32(data, startIndex: 0));

            double diff = max - min;
            double mod = val % diff;

            return (min + mod) / 1000000.0;
        }

        public bool Constraint1(double x, double y) => (x * y + x - y - 3.5) <= 0 ? true : false;
        public bool Constraint2(double x, double y) => (10 - x * y) <= 0 ? true : false;
    }
}
