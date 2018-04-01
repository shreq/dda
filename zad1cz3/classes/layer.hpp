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

    Layer(unsigned int, double, std::vector<double>);
    virtual ~Layer();

    std::vector<double> count();

    void set_inputs(const std::vector<double>);
    std::vector<double> get_outputs();
};

#endif //CLASSES_LAYER_HPP
