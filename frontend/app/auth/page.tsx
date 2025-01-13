// 'use client';
//
// import React, { useState } from "react";
// // import { divisionsData } from '@/data/location';
//
//
//
//
// const SignupForm = () => {
//   const [selectedDivision, setSelectedDivision] = useState("");
//   const [districts, setDistricts] = useState<string[]>([]);
//
//   const handleDivisionChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
//     const selected = event.target.value;
//     setSelectedDivision(selected);
//
//     // Find the districts for the selected division
//     const division = divisionsData.find((div) => div.name === selected);
//     setDistricts(division ? division.districts : []);
//   };
//
//   return (
//     <form>
//       {/* Division Dropdown */}
//       <div>
//         <label htmlFor="division">Division</label>
//         <select id="division" value={selectedDivision} onChange={handleDivisionChange}>
//           <option value="" disabled>
//             Select Division
//           </option>
//           {divisionsData.map((division) => (
//             <option key={division.name} value={division.name}>
//               {division.name}
//             </option>
//           ))}
//         </select>
//       </div>
//
//       {/* District Dropdown */}
//       <div>
//         <label htmlFor="district">District</label>
//         <select id="district" disabled={districts.length === 0}>
//           <option value="" disabled>
//             Select District
//           </option>
//           {districts.map((district) => (
//             <option key={district} value={district}>
//               {district}
//             </option>
//           ))}
//         </select>
//       </div>
//
//       <button type="submit">Submit</button>
//     </form>
//   );
// };
//
// export default SignupForm;
