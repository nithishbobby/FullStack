
import React, { useState } from 'react';
import axios from 'axios';
import './ScanPage.css';

const ScanPage = () => {
  const [selectedFile, setSelectedFile] = useState(null);
  const [description, setDescription] = useState('');
  const [loading, setLoading] = useState(false);
  const [scanResult, setScanResult] = useState(null);
  const [error, setError] = useState(null);
  const [selectedChecks, setSelectedChecks] = useState({
    methodLength: false,
    cyclomaticComplexity: false,
    codeDuplication: false,
    exceptionHandling: false,
    namingConventions: false,
    commenting: false,
    resourceManagement: false,
    hardcodedValues: false,
    codeFormatting: false,
    securityPractices: false,
  });

  const handleFileChange = (e) => {
    const file = e.target.files[0];
    if (file && file.name.endsWith('.java')) {
      setSelectedFile(file);
    } else {
      alert('Please upload a .java file');
      setSelectedFile(null);
      e.target.value = null;
    }
  };

  const handleDescriptionChange = (e) => {
    setDescription(e.target.value);
  };

  const handleCheckboxChange = (e) => {
    setSelectedChecks({
      ...selectedChecks,
      [e.target.name]: e.target.checked,
    });
  };

  const handleScan = async () => {
    if (!selectedFile) {
      alert('Please select a file to scan.');
      return;
    }
    setLoading(true);
    setError(null);
    setScanResult(null);

    try {
      const formData = new FormData();
      formData.append('file', selectedFile);
      formData.append('description', description);
      formData.append('selectedChecksJson', JSON.stringify(selectedChecks));

      // Log formData entries for debugging
      for (const [key, value] of formData.entries()) {
        console.log(`${key}: ${value}`);
      }

      const response = await axios.post('http://localhost:8080/scan', formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });
      setScanResult(response.data);
      console.log(response.data);
    } catch (error) {
      console.error('Error scanning file:', error);
      setError('An error occurred during the scan. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="scan-page">
      <h2>Scan for Code Vulnerabilities</h2>
      <div className="file-upload">
        <div>
          <label htmlFor="file-upload">Upload File (.java only):</label>
          <input id="file-upload" type="file" onChange={handleFileChange} />
        </div>
        <div>
          <input
            type="text"
            placeholder="Enter the description"
            value={description}
            onChange={handleDescriptionChange}
          />
        </div>
        <div className="checkboxes">
          <h3>Select Checks:</h3>
          {Object.keys(selectedChecks).map((check) => (
            <div key={check}>
              <label>
                <input
                  type="checkbox"
                  name={check}
                  checked={selectedChecks[check]}
                  onChange={handleCheckboxChange}
                />
                {check.replace(/([A-Z])/g, ' $1').replace(/^./, (str) => str.toUpperCase())}
              </label>
            </div>
          ))}
        </div>
        <div className="scan-buttons">
          <button onClick={handleScan} disabled={loading}>Scan</button>
        </div>
      </div>
      {loading && <p>Scanning in progress...</p>}
      {error && <p className="error">{error}</p>}
      {scanResult && (
        <div className="results">
          <h2>Scan Results</h2>
          <p><strong>Description:</strong> {scanResult.description}</p>
          <ul>
            {Object.entries(scanResult.issues).map(([issue, description], index) => (
              <li key={index}>
                <strong>{issue}:</strong> {description}
              </li>
            ))}
          </ul>
        </div>
      )}
    </div>
  );
};

export default ScanPage;
