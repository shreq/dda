#ifndef CLASSES_LAYER_HPP
#define CLASSES_LAYER_HPP
#pragma once

#include <vector>
#include <memory>

class Neuron;

class Layer
{
public:
    std::vector< std::unique_ptr<Neuron> > neurons;

    Layer(unsigned int, double, double, bool, std::vector<double>);
    virtual ~Layer();

    void count();
    void grade4output(std::vector<double>);
    void grade4hidden(std::vector<double>);
    void update();

    void set_inputs(const std::vector<double>);
    std::vector<double> get_outputs();
    std::vector<double> get_gradients();
};

#endif //CLASSES_LAYER_HPP
