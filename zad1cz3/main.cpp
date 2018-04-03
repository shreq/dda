#include "classes/neuron.hpp"
#include "classes/layer.hpp"
#include <iostream>
#include <fstream>      // ifstream
#include <string>       // string
#include <vector>       // vector
#include <cstdlib>      // srand, rand
#include <cstdio>       // NULL
#include <ctime>        // time
#include <memory>       // unique_ptr

using namespace std;

/* program explanation
input layer:
- 4 neurons
- only copies input values to hidden layer
- 'inputs' vector is used for this purpose

hidden layer:
- 1, 2 or 3 neurons
- sigmoid function

output layer:
- 4 neurons
- sigmoid function
*/

unsigned int hidden_layer_neurons = 3;
bool use_bias = 0;

void   load_stuff   (const string, vector< vector<double> >&);
void   print_vector (const vector<double>);
double error        (vector<double>, vector<double>);
void   save_error   (vector<double>);

int main()
{
    cout << "\n- - S T A R T - -";
    srand(time(NULL));
    string path = "../res/transformation.txt";

// input Layer
    vector< vector<double> > inputs; // also wanted outputs here
    load_stuff(path, inputs);

// hidden layer
    auto hidden = make_unique<Layer>( hidden_layer_neurons, 0.99, 1, use_bias, inputs[0] );

// output layer
    auto output = make_unique<Layer>( 4, 0.99, 1, use_bias, hidden->get_outputs() ); // passing trash as input for now, just for size actually

// training
    vector<double> mean_error;
    double err = (unsigned int)(-1);

    for(int z=0; err>0.001; z++)
    //for(int z=0; z<1000; z++)
    {
        err = 0;

        for(unsigned int i=0; i<inputs.size(); i++)
        {
        // forward propagation
            hidden->set_inputs( inputs[i] );
            hidden->count();

            output->set_inputs( hidden->get_outputs() );
            output->count();

        // back propagation
            output->grade4output( inputs[i] );
            hidden->grade4hidden( output->get_gradients() );

        // weights tweaking
            output->update();
            hidden->update();

        //
            err += error( inputs[i], output->get_outputs() );

        // cout-driven debuggin
            print_vector( output->get_outputs() );
        }

        err /= inputs[0].size();
        mean_error.push_back( err );
    }

    save_error( mean_error );

// final
/*
    for(unsigned int i=0; i<inputs.size(); i++)
    {
        err = 0;
        hidden->set_inputs( inputs[i] );
        hidden->count();

        output->set_inputs( hidden->get_outputs() );
        output->count();

        cout << "\nWanted:";
        print_vector( inputs[i] );
        cout << "Counted:";
        print_vector( output->get_outputs() );

        err += error( inputs[i], output->get_outputs() );
    }

    cout << "\nError:\n  " << err/inputs[0].size();
*/
// time to stop
    cout << "\n- - -S T O P- - -\n";
    return 0;
}

void load_stuff(const string path, vector< vector<double> >& v_x)
{
    ifstream fin;
    fin.open(path.c_str());

    if(!fin)
        exit(1);

	for(double tab[4]; fin >> tab[0] >> tab[1] >> tab[2] >> tab[3]; )
    {
        vector<double> temp;
        for(int i=0; i<4; i++)
            temp.push_back( tab[i] );
        v_x.push_back(temp);
    }

    fin.close();
}

void print_vector(const vector<double> vec)
{
    cout << '\n';
    for(unsigned int i=0; i<vec.size(); i++)
        cout << "  " << vec[i] << '\n';
}

double error(vector<double> wanteds, vector<double> counted)
{
    if(wanteds.size() != counted.size())
        exit(2);

    double error = 0;

    for(unsigned int i=0; i<wanteds.size(); i++)
    {
        double sub = wanteds[i] - counted[i];
        error += sub * sub;
    }

    error /= wanteds.size();

    return error;
}

void save_error(vector<double> errors)
{
    ofstream fout;
    fout.open("../res/error.dat");

    if(!fout)
        exit(3);

    for(unsigned int i=0; i<errors.size(); i++)
        fout << i+1 << ' ' << errors[i] << '\n';

    fout.close();
}
