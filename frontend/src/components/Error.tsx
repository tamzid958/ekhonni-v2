import React from 'react';

interface ErrorProps {
  message?: string;
}

const Error: React.FC<ErrorProps> = ({ message }) => {
  return (
    <div style={{ textAlign: 'center', padding: '20px', color: 'red' }}>
      <p>Error: {message || 'Something went wrong!'}</p>
    </div>
  );
};

export default Error;
