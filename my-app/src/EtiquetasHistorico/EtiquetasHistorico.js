import React, { useState, useEffect } from 'react';
import './EtiquetasHistorico.css';
import { Link } from 'react-router-dom';

const EtiquetasHistorico = ({ token }) => {
const [historico, setHistorico] = useState([]);

  useEffect(() => {
    const fetchHistorico = async () => {
      try {
        const historicoResponse = await fetch(`http://localhost:8080/v1/historicos/usuario`, {
          method: 'GET',
          headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
        });

        if (historicoResponse.ok) {
            const data = await historicoResponse.json();
            console.log(data);
            if (data) {
              // Filtrar itens com datas únicas
              const historicoSemDuplicatas = data.reduce((acc, currentItem) => {
                const exists = acc.find(item => item.data === currentItem.data);
                if (!exists) {
                  acc.push(currentItem);
                }
                return acc;
              }, []);
    
              setHistorico(historicoSemDuplicatas);
            } else {
              console.log('Dados da API estão incompletos:', data);
            }
          } else {
            console.error('Erro ao buscar Noticias:', historicoResponse.status);
          }
        } catch (error) {
          console.error('Erro durante a chamada da API:', error);
        }
      };
    
      // Chamar a função apenas se o token mudar
      if (token) {
        fetchHistorico();
      }
    }, [token]);

  return (
    <div className="container mt-4 d-flex justify-content-center align-items-center flex-column">
      <div className="historico-square">
        <h2>Histórico de Etiquetas</h2>
      <Link to="/etiquetas" className='btn btn-success'>
          Voltar
        </Link>
        {historico && historico.length > 0 ? (
          historico.map((historico) => (
            <div key={historico.data} className="card mb-3">
              <div className="card-body">
                <h2 className="card-title">{historico.etiqueta}</h2>
                <p>Data de Publicação: {historico.data}</p>
              </div>
              <hr className="my-0" />
            </div>
          ))
        ) : (
          <p className="alert alert-info">Não há histórico!</p>
        )}
      </div>
    </div>
  );
};

export default EtiquetasHistorico;