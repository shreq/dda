#include "layer.hpp"
#include "neuron.hpp"
#include <iostream>

using namespace std;

Layer::Layer(unsigned int nquantity, double learning_mp, double momentum, bool use_bias, vector<double> inputs)
{
    for(unsigned int i=0; i<nquantity; i++)
        neurons.emplace_back( make_unique<Neuron>(learning_mp, momentum, use_bias, inputs) );
}

Layer::~Layer() {}

void Layer::count()
{
    for(unsigned int i=0; i<neurons.size(); i++)
        neurons[i]->Neuron::count();
}

void Layer::grade4output(vector<double> wanteds)
{
    for(unsigned int i=0; i<neurons.size(); i++)
        neurons[i]->Neuron::grade4output( wanteds[i] );
}

void Layer::grade4hidden(vector<double> out_err)
{
    for(unsigned int i=0; i<neurons.size(); i++)
        neurons[i]->Neuron::grade4hidden( out_err );
}

void Layer::update()
{
    for(unsigned int i=0; i<neurons.size(); i++)
        neurons[i]->Neuron::update();
}

void Layer::set_inputs(const vector<double> inputs)
{
    for(unsigned int i=0; i<neurons.size(); i++)
        neurons[i]->Neuron::set_inputs( inputs );
}

vector<double> Layer::get_outputs()
{
    vector<double> temp;
    for(unsigned int i=0; i<neurons.size(); i++)
        temp.push_back( neurons[i]->get_output() );
    return temp;
}

vector<double> Layer::get_gradients()
{
    vector<double> temp;
    for(unsigned int i=0; i<neurons.size(); i++)
        temp.push_back( neurons[i]->get_gradient() );
    return temp;
}
