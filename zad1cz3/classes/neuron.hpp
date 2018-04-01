#ifndef CLASSES_NEURON_HPP
#define CLASSES_NEURON_HPP
#pragma once

#include <vector>

class Neuron
{
    double output;
    std::vector<double> inputs;     // inputs, also the wanteds in case
    std::vector<double> weights;
    std::vector<double> weightsOLD;
    double bias;                    // bias is weight itself, different for every neuron
    double biasOLD;
    double gradient;
    double learning_mp;
    double momentum;

public:
    Neuron(double, std::vector<double>);
    virtual ~Neuron();

    double sum();                       // sum of products of inputs and weights
    double count();                     // forward propagation
    double grade(double);               // count gradient, (wanted - output) * sigmoid_der, for output layer neurons
    double grade();                     // count gradient, sum * sigmoid_der, for hidden layer neurons
    void update(std::vector<double>);   // update weights and bias

    double get_output();
    void set_inputs(const std::vector<double>);
    std::vector<double> get_weights();
};

#endif //CLASSES_NEURON_HPP
