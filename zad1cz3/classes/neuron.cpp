#include "neuron.hpp"
#include <iostream>
#include <cstdlib>      // srand, rand
#include <cmath>        // exp

using namespace std;

double sigmoid   (const double arg) { return 1.0 / ( 1.0 + exp(-arg) ); }
double sigmoid_d (const double arg) { return sigmoid(arg) * (1.0 - sigmoid(arg)); }

Neuron::Neuron(double learning_mp, double momentum, bool use_bias, vector<double> inputs)
: learning_mp(learning_mp), momentum(momentum), inputs(inputs)
{
    for(unsigned int i=0; i<inputs.size(); i++)
        weights.push_back( (rand() % 99 + 1) / 100.0 ); // rand = [1; 99]
    if(use_bias)
        bias = (rand() % 99 + 1) / 100.0;
    else
        bias = 0;

    weightsOLD = weights;
    biasOLD = bias;
    output = gradient = (unsigned int)(-1);
}

Neuron::~Neuron() {}

double Neuron::sum()
{
    double sum = 0;
    for(unsigned int i=0; i<inputs.size(); i++)
        sum += weights[i] * inputs[i];
    return sum + bias;
}

double Neuron::count()
{
    output = sigmoid( sum() );
    return output;
}

double Neuron::grade4output(double wanted)
{
    gradient = wanted - output;
    return gradient;
}

double Neuron::grade4hidden(vector<double> out_err)
{
    gradient = 0;
    for(unsigned int i=0; i<inputs.size(); i++)
        gradient += weights[i] * out_err[i];
    gradient *= sigmoid_d(output);
    return gradient;
}

void Neuron::update()
{
    double temp_weight, temp_bias;
    double delta_weight;

    for(unsigned int i=0; i<weights.size(); i++)
    {
        temp_weight = weights[i];

        //weights[i] += learning_mp * gradient * inputs[i];
        //weights[i] += learning_mp * gradient * output;

        // str 99
        // delta_weight = learning_mp * error(previous_layer - next_layer) * sigmoid_d(output_current_layer) * (output_previous_layer 'T')
        weights[i] += learning_mp * gradient * sigmoid_d(output) * inputs[i];

        weightsOLD[i] = temp_weight;
    }

    temp_bias = bias;
    bias += learning_mp * gradient;
    biasOLD = temp_bias;
}

double Neuron::get_output()
{
    return output;
}

void Neuron::set_inputs(const vector<double> inputs)
{
    this->inputs = inputs;
}

vector<double> Neuron::get_inputs()
{
    return inputs;
}

vector<double> Neuron::get_weights()
{
    return weights;
}

double Neuron::get_gradient()
{
    return gradient;
}
