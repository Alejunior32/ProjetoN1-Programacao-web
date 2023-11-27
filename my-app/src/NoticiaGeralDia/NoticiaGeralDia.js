import React, { useState, useEffect } from 'react';
import './NoticiaGeralDia.css';
import { Link } from 'react-router-dom';

const NoticiaGeralDia = ({ token }) => {
const [noticias, setNoticias] = useState([]);

  useEffect(() => {
    const fetchNoticias = async () => {
      try {
        const noticiasResponse = await fetch(`http://localhost:8080/v1/noticias/usuario/todas-as-noticias-do-usuario/data-atual/`, {
          method: 'GET',
          headers: {
            'Authorization': `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
        });

        if (noticiasResponse.ok) {
          const data = await noticiasResponse.json();
          if (data && data.content) {
            setNoticias(data.content);
          } else {
            console.log('Dados da API estão incompletos:', data);
          }
        } else {
          console.error('Erro ao buscar Noticias:', noticiasResponse.status);
        }
      } catch (error) {
        console.error('Erro durante a chamada da API:', error);
      }
    };

    fetchNoticias();
  }, [token]);

  return (
    <div className="container mt-4">
      <div className="noticia-square">
        {noticias && noticias.length > 0 ? (
          noticias.map((noticia) => (
            <div key={noticia.id} className="card mb-3">
              <div className="card-body">
                <h2 className="card-title">{noticia.titulo}</h2>
                <p className="card-text">{noticia.introducao}</p>
                <p>Data de Publicação: {noticia.data_publicacao}</p>
                <p>Editorias: {noticia.editorias}</p>
              </div>
              <hr className="my-0" />
            </div>
          ))
        ) : (
          <p className="alert alert-info">Não há notícias relacionadas a estas etiquetas na data de hoje!</p>
        )}
        <Link to="/etiquetas" className='btn btn-success'>
          Voltar
        </Link>
      </div>
    </div>
  );
};

export default NoticiaGeralDia;