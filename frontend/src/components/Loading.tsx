import React from 'react';

const Loading: React.FC = () => {
  return (
    <div style={{ textAlign: 'center', padding: '20px' }}>
      <div style={{ display: 'inline-block', margin: 'auto' }}>
        <div className="spinner"></div>
      </div>
      <p>Loading...</p>
      <style>{`
        .spinner {
          width: 50px;
          height: 50px;
          border: 5px solid #f3f3f3;
          border-top: 5px solid #3498db;
          border-radius: 50%;
          animation: spin 1s linear infinite;
        }
        @keyframes spin {
          0% {
            transform: rotate(0deg);
          }
          100% {
            transform: rotate(360deg);
          }
        }
      `}</style>
    </div>
  );
};

export default Loading;
