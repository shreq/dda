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
    Neuron(double, bool, std::vector<double>);
    virtual ~Neuron();

    double sum();                   // sum of products of inputs and weights
    double count();                 // forward propagation
    double grade4output(double);    // count gradient, (wanted - output) * sigmoid_der
    double grade4hidden(std::vector<double>);    // count gradient,
    void update();                  // update weights and bias

    double get_output();
    void set_inputs(const std::vector<double>);
    std::vector<double> get_inputs();
    std::vector<double> get_weights();
    double get_gradient();
};

#endif //CLASSES_NEURON_HPP
