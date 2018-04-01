#include "layer.hpp"
#include "neuron.hpp"
#include <iostream>

using namespace std;

Layer::Layer(unsigned int nquantity, double learning_mp, vector<double> inputs)
{
    for(unsigned int i=0; i<nquantity; i++)
        neurons.emplace_back( make_unique<Neuron>(learning_mp, inputs) );

    //cout << "\nctor Layer";
}

Layer::~Layer()
{
    //cout << "\ndtor Layer";
}

void Layer::count()
{
    for(unsigned int i=0; i<neurons.size(); i++)
        neurons[i]->Neuron::count();
}

void Layer::grade(vector<double> wanteds)
{
    for(unsigned int i=0; i<neurons.size(); i++)
        neurons[i]->Neuron::grade( wanteds[i] );
}

void Layer::grade()
{
    for(unsigned int i=0; i<neurons.size(); i++)
        neurons[i]->Neuron::grade();
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
